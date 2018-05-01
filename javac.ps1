for ($i=0; $i -lt $args.Length; $i++)
{
    Invoke-Expression "javac -d .\bin $($args[$($i)])"
}